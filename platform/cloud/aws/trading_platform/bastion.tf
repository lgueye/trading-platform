# Platform key-pair
resource "aws_key_pair" "platform_key" {
  key_name   = "platform_key"
  public_key = file(var.public_key)
}

# Bastion instance
resource "aws_instance" "bastion" {
  ami           = var.aws_amis[var.aws_region]
  instance_type = "t2.micro"

  # the VPC subnet
  subnet_id = aws_subnet.frontends_1_sub.id

  # the security group
  vpc_security_group_ids = [aws_security_group.connect_to_bastion.id]

  # the public SSH key
  key_name = aws_key_pair.platform_key.key_name
}