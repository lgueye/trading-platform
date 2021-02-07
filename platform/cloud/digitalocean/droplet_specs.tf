# droplets specs
variable "droplet_image" {
  default = "ubuntu-20-04-x64"
}
variable "droplet_size" {
  default = "s-1vcpu-1gb"
}
# target environment
resource "digitalocean_tag" "target_env" {
  name = var.target_env
}
