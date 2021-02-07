# RDS
resource "aws_db_subnet_group" "db_subnet_group" {
  name        = "db_subnet_group"
  description = "db_subnet_group"
  subnet_ids  = [aws_subnet.platform_1_sub.id, aws_subnet.platform_2_sub.id]
}

resource "aws_db_instance" "db_cluster" {
  allocated_storage       = 20 # 100 GB of storage, gives us more IOPS than a lower number
  engine                  = "postgres"
  instance_class          = "db.t2.micro" # use micro if you want to use the free tier
  identifier              = var.app_name
  name                    = var.app_name
  username                = var.db_root_user     # username
  password                = var.db_root_password # password
  db_subnet_group_name    = aws_db_subnet_group.db_subnet_group.name
  multi_az                = "true" # set to true to have high availability: 2 instances synchronized with each other
  vpc_security_group_ids  = [aws_security_group.connect_to_db.id]
  storage_type            = "gp2"
  backup_retention_period = 30                                          # how long you’re going to keep your backups
  availability_zone       = aws_subnet.platform_1_sub.availability_zone # prefered AZ
  skip_final_snapshot     = true                                        # skip final snapshot when doing terraform destroy
  tags = {
    Name = "db_cluster"
  }
}

output "db_cluster_resources" {
  value = aws_db_instance.db_cluster.endpoint
}

# MQ
resource "aws_mq_broker" "broker_cluster" {
  broker_name = "broker_cluster"
  engine_type        = "ActiveMQ"
  engine_version     = "5.15.0"
  host_instance_type = "mq.t2.micro"
  security_groups    = ["${aws_security_group.connect_to_broker.id}"]

  user {
    username = var.broker_root_user
    password = var.broker_root_password
  }

  user {
    username = var.app_name
    password = var.broker_application_password
  }
}

output "broker_cluster_resources" {
  value = aws_mq_broker.broker_cluster.instances[0].endpoints
}
