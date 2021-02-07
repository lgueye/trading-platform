
# Apps key-pair

resource "aws_key_pair" "apps_key" {
  key_name   = "apps_key"
  public_key = file(var.public_key)
}

# Apps instances

resource "aws_instance" "accounts_1" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_1_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "accounts_2" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_2_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "instruments_1" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_1_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "instruments_2" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_2_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "bookings_1" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_1_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "bookings_2" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_2_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "iam_1" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_1_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "iam_2" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_2_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "market_1" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_1_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "market_2" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_2_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "traffic_1" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_1_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}

resource "aws_instance" "traffic_2" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.apps_2_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_apps.id]

  # the public SSH key
  key_name = aws_key_pair.apps_key.key_name
}
