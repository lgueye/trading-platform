# data centers
variable "primary_datacenter_name" {
  default = "fra1"
}
variable "fallback_datacenter_name" {
  default = "ams3"
}
variable "ternary_datacenter_name" {
  default = "lon1"
}
variable "primary_datacenter_role" {
  default = "primary"
}
variable "fallback_datacenter_role" {
  default = "fallback"
}
variable "ternary_datacenter_role" {
  default = "ternary"
}
