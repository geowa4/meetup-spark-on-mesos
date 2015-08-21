# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.ssh.private_key_path = "~/.vagrant.d/insecure_private_key"
  config.vm.network "private_network", type: "dhcp"
  config.ssh.insert_key = false
  # Share an additional folder to the guest VM. The first argument is
  # the path on the host to the actual folder. The second argument is
  # the path on the guest to mount the folder. And the optional third
  # argument is a set of non-required options.
  # config.vm.synced_folder "../data", "/vagrant_data"

  config.vm.provider "virtualbox" do |vb|
    vb.memory = "2048"
    vb.cpus = 2
  end

  config.vm.define "master1", primary: true do |master1|
    master1.vm.box = "ubuntu/trusty64"
  end

#  config.vm.define "master2" do |master2|
#    master2.vm.box = "ubuntu/trusty64"
#  end
#
#  config.vm.define "master3" do |master3|
#    master3.vm.box = "ubuntu/trusty64"
#  end

  config.vm.define "slave1" do |slave1|
    slave1.vm.box = "ubuntu/trusty64"
  end

  config.vm.define "slave2" do |slave2|
    slave2.vm.box = "ubuntu/trusty64"
  end

  config.vm.define "slave3" do |slave3|
    slave3.vm.box = "ubuntu/trusty64"
  end

  config.vm.provision "ansible" do |ansible|
    ansible.groups = {
      "mesos_master" => ["master1", "master2", "master3"],
      "mesos_slave" => ["slave1", "slave2", "slave3"]
    }
    ansible.extra_vars = {
      "remote_user" => "vagrant",
      "network_adapter" => "eth1"
    }
    ansible.limit = "all"
    ansible.vault_password_file = ".vault"
    ansible.playbook = "deploy/site.yml"
  end
end
