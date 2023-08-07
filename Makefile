all: stop build start

clean:
	@./mvnw clean package

build: clean
	@docker build -t quarkus-gelf-kafka -f src/main/docker/Dockerfile.jvm .

start:
	@docker-compose up -d

stop:
	@docker-compose down

logs:
	@docker-compose logs -f quarkus-gelf-kafka
