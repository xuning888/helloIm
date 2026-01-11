SERVICES := helloIm-Gateway helloIm-WebApi helloIm-delivery helloIm-message helloIm-session helloIm-store helloIm-user helloim-chatlist helloim-dispatch
VERSION := 0.1


.PHONY: build compile

build: compile
	@for service in $(SERVICES); do \
		echo "building $$service..."; \
		service_lower=$$(echo $$service | tr '[:upper:]' '[:lower:]'); \
		docker build -t $$service_lower:$(VERSION) -f ./$$service/Dockerfile ./$$service; \
	done
	@echo "build success."

compile:
	@mvn clean package -DskipTests
	@echo "compile success"