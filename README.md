# Entrando na documentação

1. Acesso o endereço: `https://ec2-54-86-242-15.compute-1.amazonaws.com/public/swagger.yaml` e aceite o certificado auto assinado
1. Acesse o [Swagger Editor](http://editor.swagger.io/)
1. Vá em `Menu` > `File` > `Import URL...`
1. Desmarque a opção `Use CORS proxy`
1. Adicione a url:
  https://ec2-54-86-242-15.compute-1.amazonaws.com/public/swagger.yaml
1. Clique em `Import`
1. Pronto, agora você pode conferir tudo passo-a-passo para poder utilizar a API e a arquitetura utilizada para sua construção.


Abaixo contem uma breve descrição das ferramentas utilizadas para construção da api. Não se importe com isso, vá direto para o passo anterior da importação ;)

Execuções via da api via [curl](curl.md)

##### Gerando Java KeyStore para habilitar o https

```sh
keytool -genkey -keystore src/main/resources/keystore.p12 -storetype PKCS12 -keyalg RSA -keysize 2048 -validity 3650 -storepass changeit -keypass changeit -alias jetty -dname "CN=com.github.netoht, OU=netoht, O=netoht, L=Sao Paulo , ST=SP, C=BR"
```

##### Docker mongodb

```sh
docker run -it -d -p 27017:27017 -p 28017:28017 --name mongodb tutum/mongodb
docker logs mongodb
docker exec -it mongodb bash
mongo admin -u admin -p qJJXa1BOoz6A
```

Executar o comando abaixo no terminal do mongodb:

```js
use mobile;
db.createUser(
   {
     user: "umobile",
     pwd: "a",
     roles: [ "readWrite", "dbAdmin" ]
   }
);
db.createCollection("driver_status");
```

##### Docker postgresql

```sh
docker run -it -p 5432:5432 -e POSTGRES_PASSWORD=a --name postgres -d postgres
docker exec -it postgres bash
su -l postgres -c "createuser -P umobile"
su -l postgres -c "createdb -O umobile mobile"
```

##### Amazon (https://[instance-name].compute-1.amazonaws.com:9000/v1/drivers/1/status)

```sh
# para acessar a máquina no EC2
chmod 0400 ~/taxis.pem
ssh -i ~/taxis.pem ubuntu@[instance-name].compute-1.amazonaws.com

# criando diretório da apliação
sudo mkdir /opt/mobile
sudo chmod 744 /opt/mobile/
sudo chown ubuntu. /opt/mobile/

# instalando o java 8
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

# adicionando regra no iptables para porta da aplicação
# porta 22 para continuar tendo acesso ssh
# redirecionando porta 443 para 9000 da aplicação
sudo iptables -A OUTPUT -p tcp --sport 22 -j ACCEPT
sudo iptables -A OUTPUT -p tcp --sport 5432 -j ACCEPT
sudo iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 443 -j REDIRECT --to-port 9000
sudo su
touch /etc/iptables.rules
iptables-save > /etc/iptables.rules
exit

# instalando o postgres
sudo apt-get install -y postgresql postgresql-contrib vim
sudo service postgresql start

# criando o usuário umobile e utilizando a senha: "a"
sudo -u postgres createuser -P umobile

# criando o banco de dados para o usuário criado anteriormente
sudo -u postgres createdb -O umobile mobile

# habilitando conexão externa
sudo vim /etc/postgresql/9.3/main/postgresql.conf
# adicionar a linha:
> listen_addresses = '*'

# habilitando acesso md5
sudo vim /etc/postgresql/9.3/main/pg_hba.conf
# adicionar ao final do arquivo linha:
> host all all 0.0.0.0/0 md5

sudo service postgresql restart

#PS: Liberar no firewall as portas no painel da EC2
```

##### Enviando e iniciando a aplicação para o EC2

```sh
mvn clean package
cp target/mobile-1.0.0-SNAPSHOT.jar target/mobile.jar
scp -i ~/taxis.pem target/mobile.jar ubuntu@ec2-54-86-242-15.compute-1.amazonaws.com:/opt/mobile/

# em desenvolvimento
java -jar target/mobile-1.0.0-SNAPSHOT.jar --server.port=9000 --server.admin.port=9001 --mongodb.host=192.168.99.100 --mongodb.port=27017 --mongodb.database=mobile --mongodb.user=umobile --mongodb.pass=a --postgres.host=192.168.99.100 --postgres.port=5432 --postgres.database=mobile --postgres.user=umobile --postgres.pass=a --redis.host=192.168.99.100 --redis.port=6379 --email.notify.to=netoht@gmail.com --db.init=true

# na máquina EC2
ssh -i ~/taxis.pem ubuntu@ec2-54-86-242-15.compute-1.amazonaws.com
```
