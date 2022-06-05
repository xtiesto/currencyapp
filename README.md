# Currency application

### Description
Service that accesses OpenExchangeRates service, and displays the gif. If the exchange rate against basic currency for today was higher than yesterday, then give a random positive gif, else negative.

### Build
Execute the following command to build the application and create a docker image with it.
* ./gradlew bootBuildImage --imageName=$IMAGE_NAME

### Run
Execute the following command to create a container from built image and run the app.
* docker run -d -p 8081:8080 --name $CONTAINER_NAME $IMAGE_NAME