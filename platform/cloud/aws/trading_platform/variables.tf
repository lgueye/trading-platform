variable "aws_region" {
  type = string
  default = "eu-central-1"
}

variable "aws_availability_zones" {
  type = list(string)
  default = ["eu-central-1a", "eu-central-1b"] 
}

variable "aws_amis" {
  type = map(string)
  default = {
    eu-central-1 = "ami-0b90a8636b6f955c1"
  }
}

variable "namespace" {
  type = string
  default = "agileinfra-io"
}

variable "env" {
  type = string
  default = "staging"
}

variable "app_name" {
  type = string
  default = "trading-platform"
}

# VPC

variable vpc_cidr_block {
  type = string
  default = "10.0.0.0/16"
}

variable "private_key" {
  default = "bastion"
}

variable "public_key" {
  default = "bastion.pub"
}

# DB
variable db_root_user {
  type = string
  default = "db_root"
}

variable db_root_password {
  type = string
}

# Broker
variable broker_root_user {
  type = string
  default = "broker_root"
}

variable broker_root_password {
  type = string
}

variable broker_application_password {
  type = string
}
