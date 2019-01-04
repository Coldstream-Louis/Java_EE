# Java EE

## Back End
Use IDEA to open the root of the back end, then use Maven 3 to import the project’s plugins and dependencies. This will take several minutes. After finishing the importing, you can use the plugin jetty in maven project menu and double click jetty_run to run the back end. Nevertheless, you need to have a Neo4j server to provide the data, you can try installing a local one to replace our server and don’t forget to change the code. 

## Front End
First you need to download and install NodeJS. After installing NodeJS, use terminal to go to the root of the front end and run <code>npm install</code> to install the node modules. Then, you can type <code>npm run dev</code> to run the front end on localhost:8080. If you want to deploy the front end, run <code>npm run build</code>, your project will be in the folder “dist”. 
