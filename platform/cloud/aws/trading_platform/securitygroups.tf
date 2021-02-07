# Bastion security-group: allow ssh from my computer

resource "aws_security_group" "connect_to_bastion" {
  vpc_id      = aws_vpc.root_vpc.id
  name        = "connect_to_bastion"
  description = "security group that allows ssh from my computer and all egress traffic"
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["172.22.22.133/32"]
  }
  tags = {
    Name = "instance-${var.namespace}-bastion"
  }
}
# ELB security-group: security group that allows 22 + 80 + 443 from bastion and 80 + 443 from Internet

resource "aws_security_group" "connect_to_elb" {
  vpc_id      = aws_vpc.root_vpc.id
  name        = "connect_to_elb"
  description = "security group that allows 22 + 80 + 443 from bastion and 80 + 443 from ELB"
  
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port       = 80
    to_port         = 80
    protocol        = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # allowing 80 access from everywhere
  }

  ingress {
    from_port       = 443
    to_port         = 443
    protocol        = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # allowing 443 access from everywhere
  }

  ingress {
    from_port       = 22
    to_port         = 22
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id] # allowing ssh access from our bastion instance
  }

  tags = {
    Name = "connect_to_elb"
  }
}

# Frontends security-group: security group that allows 22 + 80 + 443 from bastion and 80 + 443 from ELB

resource "aws_security_group" "connect_to_frontends" {
  vpc_id      = aws_vpc.root_vpc.id
  name        = "connect_to_frontends"
  description = "security group that allows 22 + 80 + 443 from bastion and 80 + 443 from ELB"
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port       = 80
    to_port         = 80
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id, aws_security_group.connect_to_elb.id] # allowing 80 access from our bastion + ELB instances
  }

  ingress {
    from_port       = 443
    to_port         = 443
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id, aws_security_group.connect_to_elb.id] # allowing 443 access from our bastion + ELB instances
  }

  ingress {
    from_port       = 22
    to_port         = 22
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id] # allowing ssh access from our bastion instance
  }

  tags = {
    Name = "connect_to_frontends"
  }
}

# Apps security-group: security group that allows 22 + 8080 from bastion and 8080 from frontends

resource "aws_security_group" "connect_to_apps" {
  vpc_id      = aws_vpc.root_vpc.id
  name        = "connect_to_apps"
  description = "security group that allows 22 + 8080 from bastion and 8080 from frontends"
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port       = 8080
    to_port         = 8080
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id, aws_security_group.connect_to_frontends.id] # allowing 8080 access from our bastion + apps instances
  }

  ingress {
    from_port       = 22
    to_port         = 22
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id] # allowing ssh access from our bastion instance
  }

  tags = {
    Name = "connect_to_apps"
  }
}

# DB security-group: security group that allows 22 + 5432 from bastion and 5432 from apps

resource "aws_security_group" "connect_to_db" {
  vpc_id      = aws_vpc.root_vpc.id
  name        = "connect_to_db"
  description = "security group that allows 22 + 5432 from bastion and 5432 from apps"

  ingress {
    from_port       = 5432
    to_port         = 5432
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id, aws_security_group.connect_to_apps.id] # allowing 5432 access from our bastion + apps instances
  }
  
  ingress {
    from_port       = 22
    to_port         = 22
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id] # allowing ssh access from our bastion instance
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
    self        = true
  }
  tags = {
    Name = "connect_to_db"
  }
}

# Broker security-group: security group that allows 22 + 61617 from bastion and 61617 from apps

resource "aws_security_group" "connect_to_broker" {
  vpc_id      = aws_vpc.root_vpc.id
  name        = "connect_to_broker"
  description = "security group that allows 22 + 61617 from bastion and 61617 from apps"

  ingress {
    from_port       = 61617
    to_port         = 61617
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id, aws_security_group.connect_to_apps.id] # allowing 61617 access from our bastion + apps instances
  }
  
  ingress {
    from_port       = 22
    to_port         = 22
    protocol        = "tcp"
    security_groups = [aws_security_group.connect_to_bastion.id] # allowing ssh access from our bastion instance
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
    self        = true
  }
  tags = {
    Name = "connect_to_broker"
  }
}
