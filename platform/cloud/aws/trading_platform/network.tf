# Virtual Private network

resource "aws_vpc" "root_vpc" {
  cidr_block       = var.vpc_cidr_block
  instance_tenancy = "default"
  enable_dns_support = "true"
  enable_dns_hostnames = "true"
  enable_classiclink = "false"
  tags = {
    Name = "vpc-${var.namespace}"
  }
}

output "vpc_id" {
  value = aws_vpc.root_vpc.id
}

# Internet GW
resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.root_vpc.id

  tags = {
    Name = "igw-${var.namespace}"
  }
}

# public route table
resource "aws_route_table" "public_rt" {
  vpc_id = aws_vpc.root_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }

  tags = {
    Name = "rt-${var.namespace}-public"
  }
}

output "public_rt_id" {
  value = aws_route_table.public_rt.id
}


# Public Subnets: frontends-{1,2}
resource "aws_subnet" "frontends_1_sub" {
  vpc_id = aws_vpc.root_vpc.id
  cidr_block = "10.0.1.0/24"
  map_public_ip_on_launch = "true"
  availability_zone = "eu-central-1a"
  tags = {
    Name = "subn-${var.namespace}-${var.app_name}-${var.env}-frontends-1"
  }

}

output "subnet_frontends_1_id" {
  value = aws_subnet.frontends_1_sub.id
}

resource "aws_subnet" "frontends_2_sub" {
  vpc_id = aws_vpc.root_vpc.id
  cidr_block = "10.0.4.0/24"
  map_public_ip_on_launch = "true"
  availability_zone = "eu-central-1b"
  tags = {
    Name = "subn-${var.namespace}-${var.app_name}-${var.env}-frontends-2"
  }

}

output "subnet_frontends_2_id" {
  value = aws_subnet.frontends_2_sub.id
}

# route associations public
resource "aws_route_table_association" "rta_frontends_1" {
  subnet_id      = aws_subnet.frontends_1_sub.id
  route_table_id = aws_route_table.public_rt.id
}

resource "aws_route_table_association" "rta_frontends_2" {
  subnet_id      = aws_subnet.frontends_1_sub.id
  route_table_id = aws_route_table.public_rt.id
}

# NAT EIP
resource "aws_eip" "nat_eip" {
  vpc = true
}

output "nat_eip_ip" {
  value = aws_eip.nat_eip.public_ip
}

resource "aws_nat_gateway" "nat_gw" {
  allocation_id = aws_eip.nat_eip.id
  subnet_id     = aws_subnet.frontends_1_sub.id
  depends_on    = [aws_internet_gateway.igw]
}

resource "aws_route_table" "private_rt" {
  vpc_id = aws_vpc.root_vpc.id
  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat_gw.id
  }

  tags = {
    Name = "rt-${var.namespace}-private"
  }
}

# Private Subnets: apps-{1,2}, platform-{1,2} (DB, broker)
resource "aws_subnet" "apps_1_sub" {
  vpc_id = aws_vpc.root_vpc.id
  cidr_block = "10.0.2.0/24"
  availability_zone = "eu-central-1a"
  tags = {
    Name = "subn-${var.namespace}-${var.app_name}-${var.env}-apps-1"
  }

}

output "subnet_apps_1_id" {
  value = aws_subnet.apps_1_sub.id
}

resource "aws_subnet" "apps_2_sub" {
  vpc_id = aws_vpc.root_vpc.id
  cidr_block = "10.0.5.0/24"
  availability_zone = "eu-central-1b"
  tags = {
    Name = "subn-${var.namespace}-${var.app_name}-${var.env}-apps-2"
  }

}

output "subnet_apps_2_id" {
  value = aws_subnet.apps_2_sub.id
}

resource "aws_subnet" "platform_1_sub" {
  vpc_id = aws_vpc.root_vpc.id
  cidr_block = "10.0.3.0/24"
  availability_zone = "eu-central-1a"
  tags = {
    Name = "subn-${var.namespace}-${var.app_name}-${var.env}-platform-1"
  }

}

output "subnet_platform_1_id" {
  value = aws_subnet.platform_1_sub.id
}

resource "aws_subnet" "platform_2_sub" {
  vpc_id = aws_vpc.root_vpc.id
  cidr_block = "10.0.6.0/24"
  availability_zone = "eu-central-1b"
  tags = {
    Name = "subn-${var.namespace}-${var.app_name}-${var.env}-platform-2"
  }

}

output "subnet_platform_2_id" {
  value = aws_subnet.platform_2_sub.id
}

# route associations private
resource "aws_route_table_association" "rta_apps_1" {
  subnet_id      = aws_subnet.apps_1_sub.id
  route_table_id = aws_route_table.private_rt.id
}

resource "aws_route_table_association" "rta_apps_2" {
  subnet_id      = aws_subnet.apps_2_sub.id
  route_table_id = aws_route_table.private_rt.id
}

resource "aws_route_table_association" "rta_platform_1" {
  subnet_id      = aws_subnet.platform_1_sub.id
  route_table_id = aws_route_table.private_rt.id
}

resource "aws_route_table_association" "rta_platform_2" {
  subnet_id      = aws_subnet.platform_2_sub.id
  route_table_id = aws_route_table.private_rt.id
}
