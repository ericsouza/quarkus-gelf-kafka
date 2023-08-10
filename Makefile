SEMVER_INCREMENT?="patch"

VERSION=$(shell ./mvnw semver:verify-current -Dforce-stdout -q)

all: stop build start

clean:
	@./mvnw clean package

run-local: clean
	@quarkus dev

build: bump clean
	@echo "Version ${VERSION} will be build"
	docker build -t quarkus-gelf-kafka -f src/main/docker/Dockerfile.jvm .

start:
	@docker-compose up -d

start-deps-only:
	@docker-compose up -d db redpanda console

stop:
	@docker-compose down

logs:
	@docker-compose logs -f quarkus-gelf-kafka

.PHONY: bump
bump:
	@mvn semver:increment-$(SEMVER_INCREMENT)

.PHONY: release
release: build
	@git add pom.xml \
		&& git commit -m "release version $(VERSION)" \
		&& git push

.PHONY: default-java-release
default-java-release:
	@mvn semver:finalize-current \
		&& echo "Version $(VERSION) will be released" \
		&& git add pom.xml \
		&& git commit -m "prepare release" \
		&& mvn semver:increment-patch -Dsnapshot=true \
		&& git add pom.xml \
	 	&& git commit -m "prepare for next iteration" \
		&& git push
