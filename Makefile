all: stop build start

clean:
	@./mvnw clean package

run-local: clean
	@quarkus dev

build: clean
	@docker build -t quarkus-gelf-kafka -f src/main/docker/Dockerfile.jvm .

start:
	@docker-compose up -d

start-deps-only:
	@docker-compose up -d db redpanda console

stop:
	@docker-compose down

logs:
	@docker-compose logs -f quarkus-gelf-kafka
