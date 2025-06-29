CONTAINER_ENGINE := $(shell if command -v podman &>/dev/null; then echo podman; else echo docker; fi)
DEPENDENCIES := $(CONTAINER_ENGINE) javac

.PHONY: check-deps clean dev container-build container-run container-stop container-logs container-destroy help

check-deps:
	@for cmd in $(DEPENDENCIES); do \
		if ! command -v $$cmd &>/dev/null; then \
			echo "Couldn't find $$cmd!"; \
			exit 1; \
		fi; \
	done

clean: check-deps
	@./mvnw --quiet clean;
	@echo "Build artifacts cleaned";

dev: check-deps
	@./mvnw quarkus:dev

container-build: check-deps
	@$(CONTAINER_ENGINE) compose build

container-run: check-deps
	@$(CONTAINER_ENGINE) compose up --detach

container-stop:
	@$(CONTAINER_ENGINE) compose down

container-logs:
	@$(CONTAINER_ENGINE) compose logs --follow

container-destroy:
	@$(CONTAINER_ENGINE) compose down --volumes --rmi local

help:
	@echo "Available targets:"
	@echo "  check-deps        - Check for required system dependencies"
	@echo "  clean             - Clean build artifacts"
	@echo "  dev               - Start project in development mode"
	@echo "  container-build   - Build project in containers and create container images"
	@echo "  container-run     - Run those containers"
	@echo "  container-stop    - Stop project containers"
	@echo "  container-logs    - Show project container logs"
	@echo "  container-destroy - Stop and delete those containers, networks, volumes, and images"
	@echo "  help              - Show this help message"
