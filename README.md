![license](https://img.shields.io/github/license/imalric/ToolAndEquipmentAPI?style=for-the-badge&color=blueviolet)
![lastcommit](https://img.shields.io/github/last-commit/imalric/ToolAndEquipmentAPI?style=for-the-badge)
![release](https://img.shields.io/github/release-date/imalric/ToolAndEquipmentAPI?style=for-the-badge) 
![maintained](https://img.shields.io/badge/maintained-no%20(since%2017/02/2023)-inactive?style=for-the-badge)

<span align="center">
    <h1 style="font-weight:bold">
    <img width="40" style="vertical-align:middle" src="https://em-content.zobj.net/thumbs/120/microsoft/319/toolbox_1f9f0.png"/> 
    <br/>
        API REST para Sistema de Manutenção de Equipamentos
    </h1>
    <p>execução, envio e pesquisa de dados.</p>
</span>

<h2><img width="22" style="vertical-align:middle" src="https://em-content.zobj.net/thumbs/120/microsoft/319/sleeping-face_1f634.png"/> TL;DR;</h2>
API REST para execução, envio, pesquisa e controle de dados em um contexto de sistema para manutenção de equipamentos/ferramentas.
<br/>
```shell
./mvnw spring-boot:run
```
<a href="#commands">➤ Comandos da API</a>

<h2><img width="22" style="vertical-align:middle" src="https://em-content.zobj.net/thumbs/120/microsoft/319/bookmark-tabs_1f4d1.png"/> Sumário</h2>
<ol>
    <li><a href="#tech">Tecnologias Utilizadas</a></li>
    <li><a href="#about">Sobre o Projeto</a></li>
    <li><a href="#structure">Estrutura URI do Sistema</a></li>
    <li><a href="#test">Testar o Sistema</a></li>
    <li><a href="#use">Utilizar o Sistema</a></li>
    <li><a href="#plans">Planos/Metas para o Futuro</a></li>
    <li><a href="#license">Licença</a></li>
</ol>

<h2 id="tech"><img width="22" style="vertical-align:middle" src="https://em-content.zobj.net/thumbs/120/microsoft/319/man-technologist_1f468-200d-1f4bb.png"/> Tecnologias Utilizadas:</h2>

<img style="vertical-align:middle" width="25" src="https://upload.wikimedia.org/wikipedia/commons/9/9c/IntelliJ_IDEA_Icon.svg"/>
IntelliJ IDEA
<br/>
<br/>
<img style="vertical-align:middle" width="25" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/java/java-plain.svg"/>
Java 17
<br/>
<br/>
<img style="vertical-align:middle" width="25" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/spring/spring-original.svg"/>
Spring-Boot & SpringMVC
<br/>
<br/>
<img style="vertical-align:middle" width="25" src="https://upload.wikimedia.org/wikipedia/commons/7/7e/Apache_Feather_Logo.svg"/>
Maven
<br/>
<br/>
<img style="vertical-align:middle" width="25" src="https://www.vectorlogo.zone/logos/hibernate/hibernate-icon.svg"/>
JPA & Hibernate

<h2 id="about"><img width="22" style="vertical-align:middle" src="https://em-content.zobj.net/thumbs/120/microsoft/319/information_2139-fe0f.png"/> Sobre o Sistema:</h2>
Esta API foi desenvolvida com Java, utilizando Spring-Boot e Maven, SpringMVC, JPA e Hibernate,
utilizando a metodologia REST, contendo testes unitários.
<br/>
<br/>
Foi desenvolvida pensando num sistema de manutenções de equipamentos/ferramentas, onde devem ser
enviados dados referentes à nova ordens de serviço, interrupção e baixa das mesmas, assim como a busca destas e de outras
informações, como dados de clientes, etc.
<br/>
<br/>
Todas as pesquisas com mais de um resultado trazem paginação.

<h2 id="structure"><img width="22" style="vertical-align:middle" src="https://em-content.zobj.net/thumbs/120/microsoft/319/man-construction-worker_1f477-200d-2642-fe0f.png"/>️ Estrutura URI do Sistema:</h2>
<h3>Ordens de Serviço</h3>
<details>
<summary><strong>/order</strong> <small> > ordens de serviço</small></summary>

<h5>/new <small>> criar nova ordem de serviço </small>[POST]</h5>
<h5>/interrupt/{id} <small>> interrompe uma ordem de serviço por ID </small>[PUT]</h5>
<h5>/resume/{id} <small>> retoma uma ordem de serviço interrompida por ID </small>[PUT]</h5>
<h5>/close/{id} <small>> baixa uma ordem de serviço por ID </small>[PUT]</h5>
<h5>/{id} <small>> excluir uma ordem de serviço por ID </small>[DELETE]</h5>
<h5>/find/{id} <small>> encontrar uma ordem de serviço por ID </small>[GET]</h5>
<h5>/find/pending <small>> encontrar todas as ordens de serviço pendentes </small>[GET]</h5>
<h5>/find/customer/{id}/pending <small>> encontrar todas as ordens de serviço pendentes pelo
ID do cliente </small>[GET]</h5>
<h5>/find/staff/{id}/pending <small>> encontrar todas as ordens de serviço pendentes pelo
ID do funcionário </small>[GET]</h5>
<h5>/find/closed <small>> encontrar todas as ordens de serviço baixadas </small>[GET]</h5>
<h5>/find/customer/{id}/closed <small>> encontrar todas as ordens de serviço baixadas pelo
ID do cliente </small>[GET]</h5>
<h5>/find/staff/{id}/closed <small>> encontrar todas as ordens de serviço baixadas pelo
ID do funcionário </small>[GET]</h5>

</details>

<h3>Ferramenta/Equipamento</h3>
<details>
<summary><strong>/tool</strong> <small> > ferramentas/equipamentos</small></summary>

<h5>/find/{id} <small>> encontrar uma ferramenta/equipamento por ID </small>[GET]</h5>
<h5>/find/active <small>> encontrar todas as ferramentas/equipamentos ativos </small>[GET]</h5>

</details>

<h3>Cliente</h3>
<details>
<summary><strong>/customer</strong> <small> > clientes</small></summary>

<h5>/find/{id} <small>> encontrar um cliente por ID </small>[GET]</h5>
<h5>/find/active <small>> encontrar todos os clientes ativos </small>[GET]</h5>

</details>

<h3>Funcionário</h3>
<details>
<summary><strong>/staff</strong> <small> > funcionários</small></summary>

<h5>/find/{id} <small>> encontrar um funcionário por ID </small>[GET]</h5>
<h5>/find/active <small>> encontrar todos os funcionários ativos </small>[GET]</h5>

</details>

<h2 id="test"><img style="vertical-align:middle" width="22" src="https://em-content.zobj.net/thumbs/120/microsoft/319/test-tube_1f9ea.png"/> Testar o Sistema:</h2>
Os testes unitários do sistema podem ser feitos por meio do comando Maven:
```shell
./mvnw test
```

<h2 id="use"><img style="vertical-align:middle" width="22" src="https://em-content.zobj.net/thumbs/120/microsoft/319/desktop-computer_1f5a5-fe0f.png"/> Utilizar o Sistema:</h2>
<img width="22" style="vertical-align:middle" src="https://em-content.zobj.net/thumbs/120/microsoft/319/warning_26a0-fe0f.png"/>
**Nota Importante:**
Todas as pesquisas com mais de um resultado trazem paginação.
Para controlar o número de resultados e a página acessada, utilize:
```url
?page={número da página}&size={número de resultados}
```
**EXEMPLO**:
```url
http://localhost:8080/order/find/pending?page=1&size=15
```
Por padrão o número de resultados de cada página é **10**, sempre começando pela página **0**.
<h3 id="commands"><img style="vertical-align:middle" width="22" src="https://upload.wikimedia.org/wikipedia/commons/5/51/Windows_Terminal_logo.svg"/> Inicializando a API:</h3>
Execute o sistema por meio do comando Maven:
```shell
./mvnw spring-boot:run
```

Após isso, prossiga utilizando algum dos métodos listados abaixo:

<details>
<summary><img style="vertical-align:middle" width="22" src="https://raw.githubusercontent.com/devicons/devicon/df98428ff8c87f28e1c8901c89824b70136bb9c3/icons/postman/postman-original.svg"/><strong> Utilizando Postman</strong></summary>

<details>
<summary><strong>Ordem de Serviço</strong> <small>("/order")</small></summary>

<h4>Criar uma nova ordem de serviço. <small>("/order/new")</small></h4>
```url
POST http://localhost:8080/order/new
```
```JSON
{
  "order_desc": "<descrição do problema>",
  "tool": {
    "id": "<id #opcional (se existir um ferramenta/equipamento com esse ID, irá referenciá-lo)>",
    "tool_type": "<tipo de ferramenta/equipamento (ex:lavadora de pressão)>",
    "tool_brand": "<marca da ferramenta/equipamento (ex:WAP)>"
  },
  "customer": {
    "id": "<id #opcional (se existir um cliente com esse ID, irá referenciá-lo)>",
    "fullname": "<nome do cliente>",
    "cpf": "<cpf do cliente>",
    "phone": "<telefone do cliente>",
    "email": "<email do cliente>"
  },
  "staff": {
    "id": "<id #opcional (se existir um funcionário com esse ID, irá referenciá-lo)>",
    "fullname": "<nome do funcionário>",
    "cpf": "<cpf do funcionário>",
    "role": "<cargo do funcionário>"
  },
  "begin_date": "<data de inicio da ordem de serviço (yyyy-MM-dd)>"
}
```

<h4>Encontrar uma ordem de serviço por ID. <small>("order/find/<strong>{id}</strong>")</small></h4>
```url
GET http://localhost:8080/order/find/{id}
```

<details>
<summary>Encontrar ordens de serviço pendentes... </summary>

<h4><strong>PENDENTES > TODAS...</strong> <small>("order/find/pending")</small></h4>

```url
GET http://localhost:8080/order/find/pending
```

<h4><strong>PENDENTES > POR CLIENTE...</strong> <small>("/order/find/customer/<strong>{id}</strong>/pending")</small></h4>

```url
GET http://localhost:8080/order/find/customer/{id do cliente}/pending
```

<h4><strong>PENDENTES > POR FUNCIONÁRIO...</strong> <small>("/order/find/staff/<strong>{id}</strong>/pending")</small></h4>

```url
GET http://localhost:8080/order/find/staff/{id do funcionário}/pending
```

</details>

<br/>

<details>
<summary>Encontrar ordens de serviço baixadas... </summary>

<h4><strong>BAIXADAS > TODAS...</strong> <small>("order/find/closed")</small></h4>

```url
GET http://localhost:8080/order/find/closed
```

<h4><strong>BAIXADAS > POR CLIENTE...</strong> <small>("/order/find/customer/<strong>{id}</strong>/closed")</small></h4>

```url
GET http://localhost:8080/order/find/customer/{id do cliente}/closed
```

<h4><strong>BAIXADAS > POR FUNCIONÁRIO...</strong> <small>("/order/find/staff/<strong>{id}</strong>/closed")</small></h4>

```url
GET http://localhost:8080/order/find/staff/{id do funcionário}/closed
```

</details>

<h4><strong>Interromper uma ordem de serviço por ID. <small>("/order/interrupt/<strong>{id}</strong>")</small></strong></h4>
```url
PUT localhost:8080/order/interrupt/{id da ordem de serviço}
```
```JSON
{ 
    "action_desc": "<descrição da motivação da interrupção>",
    "staff": {"id": "<id do funcionário responsável>"} 
}
```

<h4><strong>Retomar uma ordem de serviço interrompida por ID. <small>("/order/resume/<strong>{id}</strong>")</small></strong></h4>
```url
PUT localhost:8080/order/resume/{id da ordem de serviço}
```
```JSON
{
  "action_desc": "<descrição da motivação da retomada>",
  "staff": {"id": "<id do funcionário responsável>"}
}
```

<h4><strong>Baixar uma ordem de serviço por ID. <small>("/order/close/<strong>{id}</strong>")</small></strong></h4>
```url
PUT localhost:8080/order/close/{id da ordem de serviço}
```
```JSON
{
  "action_desc": "<descrição da motivação/processo da baixa>",
  "staff": {"id": "<id do funcionário responsável>"}
}
```

<h4><strong>Excluír uma ordem de serviço por ID. <small>("/order/<strong>{id}</strong>")</small></strong></h4>
```url
DELETE localhost:8080/order/{id da ordem de serviço}
```

</details>

<details>
<summary><strong>Ferramenta/Equipamento</strong> <small>("/tool")</small></summary>

<h4>Encontrar uma ferramenta/equipamento por ID. <small>("/tool/find/<strong>{id}</strong>")</small></h4>
```url
GET http://localhost:8080/tool/find/{id da ferramenta/equipamento}
```

<h4>Encontrar todas as ferramentas/equipamentos ativos. <small>("/tool/find/active")</small></h4>
```url
GET http://localhost:8080/tool/find/active
```

</details>

<details>
<summary><strong>Cliente</strong> <small>("/customer")</small></summary>

<h4>Encontrar um cliente por ID. <small>("/customer/find/<strong>{id}</strong>")</small></h4>
```url
GET http://localhost:8080/customer/find/{id do cliente}
```

<h4>Encontrar todos os clientes ativos. <small>("/customer/find/active")</small></h4>
```url
GET http://localhost:8080/customer/find/active
```

</details>

<details>
<summary><strong>Funcionário</strong> <small>("/staff")</small></summary>

<h4>Encontrar um funcionário por ID. <small>("/staff/find/<strong>{id}</strong>")</small></h4>
```url
GET http://localhost:8080/staff/find/{id do funcionário}
```

<h4>Encontrar todos os funcionários ativos. <small>("/staff/find/active")</small></h4>
```url
GET http://localhost:8080/staff/find/active
```

</details>

</details>

<br/>

<details>
<summary><img style="vertical-align:middle" width="25" src="https://raw.githubusercontent.com/curl/curl-www/5fc82e3f291cd33cce5ad61d92d50e78975d301f/logo/curl-symbol.svg"/> <strong>Utilizando cURL</strong></summary>

<details>
<summary><strong>Ordem de Serviço</strong> <small>("/order")</small></summary>

<h4>(POST) Criar uma nova ordem de serviço. <small>("/order/new")</small></h4>
```shell
curl --location 'localhost:8080/order/new' \
--header 'Content-Type: application/json' \
--data-raw '{
    "order_desc": <descrição do problema>,
    "tool": {
        "id": <id #opcional (se existir um ferramenta/equipamento com esse ID, irá referenciá-lo)>,
        "tool_type": <tipo de ferramenta/equipamento (ex:lavadora de pressão)>,
        "tool_brand": <marca da ferramenta/equipamento/equipamento (ex:WAP)>
    },
    "customer": {
        "id": <id #opcional (se existir um cliente com esse ID, irá referenciá-lo)>,
        "fullname": <nome do cliente>,
        "cpf": <cpf do cliente>,
        "phone": <telefone do cliente>,
        "email": <email do cliente>
    },
    "staff": {
        "id": <id #opcional (se existir um funcionário com esse ID, irá referenciá-lo)>,
        "fullname": <nome do funcionário>,
        "cpf": <cpf do funcionário>,
        "role": <cargo do funcionário>
    },
    "begin_date": <data de inicio da ordem de serviço (yyyy-MM-dd)>
}'
```

<h4>(GET) Encontrar uma ordem de serviço por ID. <small>("order/find/<strong>{id}</strong>")</small></h4>
```shell
curl --location 'localhost:8080/order/find/<id da ordem de serviço>' \
--data ''
```

<details>
<summary>(GET) Encontrar ordens de serviço pendentes... </summary>

<h4><strong>PENDENTES > TODAS...</strong> <small>("order/find/pending")</small></h4>
```shell
curl --location 'localhost:8080/order/find/pending' \
--data ''
```

<h4><strong>PENDENTES > POR CLIENTE...</strong> <small>("/order/find/customer/<strong>{id}</strong>/pending")</small></h4>
```shell
curl --location 'localhost:8080/order/find/customer/<id do cliente>/pending' \
--data ''
```

<h4><strong>PENDENTES > POR FUNCIONÁRIO...</strong> <small>("/order/find/staff/<strong>{id}</strong>/pending")</small></h4>
```shell
curl --location 'localhost:8080/order/find/customer/<id do funcionário>/pending' \
--data ''
```

</details>

<br/>

<details>
<summary>(GET) Encontrar ordens de serviço baixadas... </summary>

<h4><strong>BAIXADAS > TODAS...</strong> <small>("order/find/closed")</small></h4>
```shell
curl --location 'localhost:8080/order/find/closed' \
--data ''
```

<h4><strong>BAIXADAS > POR CLIENTE...</strong> <small>("/order/find/customer/<strong>{id}</strong>/closed")</small></h4>
```shell
curl --location 'localhost:8080/order/find/customer/<id do cliente>/closed' \
--data ''
```

<h4><strong>BAIXADAS > POR FUNCIONÁRIO...</strong> <small>("/order/find/staff/<strong>{id}</strong>/closed")</small></h4>
```shell
curl --location 'localhost:8080/order/find/customer/<id do funcionário>/closed' \
--data ''
```

</details>

<h4><strong>(PUT) Interromper uma ordem de serviço por ID. <small>("/order/interrupt/<strong>{id}</strong>")</small></strong></h4>
```shell
curl --location --request PUT 'localhost:8080/order/interrupt/<id da ordem de serviço>' \
--header 'Content-Type: application/json' \
--data '{ 
"action_desc": <descrição da motivação da interrupção>,
"staff": {"id": <id do funcionário responsável>} 
}
'
```

<h4><strong>(PUT) Retomar uma ordem de serviço interrompida por ID. <small>("/order/resume/<strong>{id}</strong>")</small></strong></h4>
```shell
curl --location --request PUT 'localhost:8080/order/resume/<id da ordem de serviço>' \
--header 'Content-Type: application/json' \
--data '{ 
"action_desc": <descrição da motivação da retomada>,
"staff": {"id": <id do funcionário responsável>} 
}
'
```

<h4><strong>(PUT) Baixar uma ordem de serviço por ID. <small>("/order/close/<strong>{id}</strong>")</small></strong></h4>
```shell
curl --location --request PUT 'localhost:8080/order/close/<id da ordem de serviço>' \
--header 'Content-Type: application/json' \
--data '{ 
"action_desc": <descrição da motivação/processo da baixa>,
"staff": {"id": <id do funcionário responsável>} 
}
'
```

<h4><strong>(DELETE) Excluír uma ordem de serviço por ID. <small>("/order/<strong>{id}</strong>")</small></strong></h4>
```shell
curl --location --request DELETE 'localhost:8080/order/<id da ordem de serviço>' \
--data ''
```

</details>

<details>
<summary><strong>Ferramenta/Equipamento</strong> <small>("/tool")</small></summary>

<h4>(GET) Encontrar uma ferramenta/equipamento por ID. <small>("/tool/find/<strong>{id}</strong>")</small></h4>
```shell
curl --location 'localhost:8080/tool/find/<id da ferramenta/equipamento>' \
--data ''
```

<h4>(GET) Encontrar todas as ferramentas/equipamentos ativos. <small>("/tool/find/active")</small></h4>
```shell
curl --location 'localhost:8080/tool/find/active' \
--data ''
```

</details>

<details>
<summary><strong>Cliente</strong> <small>("/customer")</small></summary>

<h4>(GET) Encontrar um cliente por ID. <small>("/customer/find/<strong>{id}</strong>")</small></h4>
```shell
curl --location 'localhost:8080/customer/find/<id do cliente>' \
--data ''
```

<h4>(GET) Encontrar todos os clientes ativos. <small>("/customer/find/active")</small></h4>
```shell
curl --location 'localhost:8080/customer/find/active' \
--data ''
```

</details>

<details>
<summary><strong>Funcionário</strong> <small>("/staff")</small></summary>

<h4>(GET) Encontrar um funcionário por ID. <small>("/staff/find/<strong>{id}</strong>")</small></h4>
```shell
curl --location 'localhost:8080/staff/find/<id do funcionário>' \
--data ''
```

<h4>(GET) Encontrar todos os funcionários ativos. <small>("/staff/find/active")</small></h4>
```shell
curl --location 'localhost:8080/staff/find/active' \
--data ''
```

</details>

</details>

<h2 id="plans"><img style="vertical-align:middle" width="25" src="https://em-content.zobj.net/thumbs/120/microsoft/319/telescope_1f52d.png"/> Planos/Metas para o Futuro:</h2>
<ul>
    <li>Criação de funcionários/clientes/equipamentos diretamente por suas URIs. <small>ex:customer/new</small></li>
    <li>Criação de JavaDocs para o sistema.</li>
    <li>Criação de uma interface web para melhor acesso à API.</li>
    <li>Criação de controle de usuários autorizados ao uso da API.</li>
    <li>Criação de controle de permissão.</li>
    <li>Implementar pesquisa de pedidos baixados por período de datas.</li>
</ul>

Implementações tanto das funcionalidades acima como outras não planejadas serão notadas no **changelog detalhado**
de cada **release**. 
A criação de um changelog dedicado está a ser cogitada.

<h2 id="license"><img style="vertical-align:middle" width="25" src="https://em-content.zobj.net/thumbs/120/microsoft/319/balance-scale_2696-fe0f.png"/> Licença</h2>

Este projeto foi licenciado sob os termos de licença do **MIT**, para mais detalhes confira a documentação no arquivo <a href="https://github.com/imAlric/ToolAndEquipmentAPI/blob/main/LICENSE">LICENSE</a>.