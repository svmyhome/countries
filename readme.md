### Примеры запросов GQL

Запрос по ID
```graphql
query Countries($id: ID!) {
  country(id: $id) {
    id,
    name,
    code
  }
}
```
Запрос всех стран
```graphql
query allCountries {
    countries {
      id,
      name,
      code
    }
}
```
Добавление страны

**Запрос**
```graphql
mutation AddCountry($name: String!, $code: String!, $coordinates: String) {
  addCountry(input: {name: $name, code: $code, coordinates: $coordinates}) {
    id
    name
    code
    coordinates
  }
}
```
**тело запроса**
```json
{
  "name": "Gonduras",
  "code":  "Go",
  "coordinates": "[cks nckjsdncjnsdkjn]"
}
```
Обновление страны

**Запрос**
```graphql
mutation updateCountry($code: String!, $name: String!, $coordinates: String) {
    updateCountry(code: $code, input: {name: $name, code: $code, coordinates: $coordinates}) {
        id
        name
        code
        coordinates
    }
}
```
**тело запроса**
```json
{
  "code": "Go",
  "name": "Gonduras2",
  "coordinates": "[cks nckjsdncjns22dkjn1]"
}
```
