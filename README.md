# School registration API

This project show an API REST example simulating a school registration

## Installation
To run this program you need only docker and maven.

### Steps
##### 1
```bash
cd schoolregistration
```
##### 2
```bash 
mvn clean package -DskipTests
```

##### 3
```bash 
docker-compose up -d --build
```

## Usage
After of docker finished the task all the configuration its ready to use the API you can visit the next url to see the endpoints available with this program.

```html
http://localhost:8080/swagger-ui.html
```

## Contributing
You are free to do contributions to this program.

## License
[MIT](https://choosealicense.com/licenses/mit/)