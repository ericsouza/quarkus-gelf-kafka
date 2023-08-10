VERSION=$(shell ./mvnw semver:verify-current -Dforce-stdout -q)

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

.PHONY:
release:
	@mvn semver:finalize-current
	@echo "Version $(VERSION) will be released"
	@git add pom.xml \
		&& git diff --quiet && git diff --staged --quiet || git commit -m "prepare release" \
		&& mvn semver:increment-patch -Dsnapshot=true \
		&& git add pom.xml \
	 	&& git diff --quiet && git diff --staged --quiet || git commit -m "prepare for next iteration" \
		&& git push