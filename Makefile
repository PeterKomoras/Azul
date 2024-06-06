check_and_test: FORCE
	mvn test

lint: FORCE
	mvn checkstyle:check

format: FORCE
	mvn googleformatter:format
FORCE: ;
