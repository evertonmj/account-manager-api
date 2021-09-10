### Account Manager API
This REST API provides a system to manage financial accounts.

Originally, this API was developed in portuguese language and besides that, the endpoints and attributes are in portuguese.

In the near future, everything will be translated to english. 
  
  - Domain models:
    ```
    * Account entity:
    ```
    | Conta | Type | Description | 
    |-|-|-|
    | idConta | Numeric | Account ID| 
    | idPessoa | Numeric | Person ID |
    | saldo | Double | Account balance |
    | limiteSaqueDiario | Double | Daily withdraw limit |
    | flagAtivo | Boolean | Active account flag |
    | tipoConta | Numeric | Account type |
    | dataCriacao | Date | Creation date |

    ```
    * Account Transactions entity
    ```
    | Transacoes | Type | Description | 
    |-|-|-|
    | idTransacao | Numeric | Transaction ID |
    | idConta | Numeric | Account ID |
    | valor | Double | Transaction ammount |
    | dataTransacao | Date | Transaction date |

    ```
    * Person entity
    ```
    | Pessoas | Type | Description |
    |-|-|-|
    | idPessoa | Numeric | Person ID |
    | nome | String | Person name |
    | cpf | String | Brazilian identification ID |
    | dataNascimento | Date | Birth date |

  - API Functionalities:
    ```
    * Create a new person
    * Create a new account;
    * Deposit funds in an account 
    * Check account balance
    * Withdraw funds from an account
    * Block an account 
    * Retrive transactions extract from an account
    ```
### API Documentation
After the application is up and running, it is just access the following endpoint:

http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

Also, in DOCS folder there is an exported file api-swagger.json with the documentation.

### Tech Stack
    * Programming language: Java v1.8
    * Framework: Spring Boot v2.4.2
    * IDE: Intellij 2021.1
    * Database engine: MySQL v8.0.23

### File Structure
    * DOCS: Project documentation
        i. api-swagger.json - JSON file with API endpoints
        ii. AWS-Architecture-Proposal.pdf
    * SQL: MySQL scripts with database dump
    * SRC: Application source code
