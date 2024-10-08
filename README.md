# api-processos-juridicos

Esta é uma API desenvolvida para gerenciar processos jurídicos, incluindo o cadastro de usuários, partes envolvidas, ações jurídicas e processos. A API é construída utilizando o framework Spring Boot e é estruturada para facilitar a manutenção e a escalabilidade.

## Índice

- [Tecnologias](#tecnologias)
- [Instalação](#instalação)
- [Endpoints](#endpoints)
- [Exemplo de Requisições](#exemplo-de-requisições)

## Tecnologias

- Java 17
- Spring Boot
- Hibernate
- JPA
- Maven
- Lombok
- PostgreSQL
- Sonarqube
- Jacoco
- Junit
- Mockito


## Instalação

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu_usuario/api_processos_juridicos.git
   cd api_processos_juridicos

## EndPoints
- Cadastrar Usuário: POST /usuario/cadastrar
- Login: POST /usuario/login
- Editar Pessoa: PUT /pessoa/editar/{idPessoa}
- Criar Processo: POST /processos-juridicos
- Buscar Processo: GET /processos-juridicos/buscar/{numero}
- Editar Processo: PUT /processos-juridicos/{numeroProcesso}
- Buscar por Status: GET /processos-juridicos/buscar/por-status?statusProcesso={status}
- Buscar por Data de Abertura: GET /processos-juridicos/buscar/por-data-abertura?dataAbertura={data}
- Arquivar Processo: PUT /processos-juridicos/arquivar/{numeroProcesso}
- Registrar Ação: POST /acao
- Buscar Ações do Processo: GET /acao/{numeroProcesso}
- Buscar Ações do Processo por Tipo: GET /acao/por-tipo/{numeroProcesso}?tipo={tipo}

  ## Exemplo de Requisições
```
POST /usuario/cadastrar
Content-Type: application/json

{
  "email": "usuario@example.com",
  "senha": "senhaSegura"
}

```


```
POST /usuario/login
Content-Type: application/json

{
  "email": "usuario@example.com",
  "senha": "senhaSegura"
}

```


```
PUT /pessoa/editar/1
Content-Type: application/json

{
  "nomeCompleto": "Nome Atualizado",
  "inscricaoFederal": "28.941.462/0001-22",
  "tipoParte": "AUTOR",
  "telefone": "987654321",
  "email": "pessoa_atualizada@example.com"
}


```

```
POST /processos-juridicos
Content-Type: application/json

{
  "numeroProcesso": "12345",
  "dataAbertura": "2024-01-01",
  "descricaoCaso": "Descrição do caso",
  "status": "ATIVO",
  "partesEnvolvidas": [
    {
      "nomeCompleto": "Nome da Pessoa",
      "inscricaoFederal": "28.941.462/0001-22",
      "tipoParte": "AUTOR",
      "telefone": "123456789",
      "email": "pessoa@example.com"
    }
  ]
}

```
  
