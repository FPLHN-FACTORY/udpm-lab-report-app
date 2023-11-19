# Stage 1: Build the React app
FROM node:16 AS build
WORKDIR /app
COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json
RUN npm install --force
COPY . /app
RUN npm run build

# Stage 2: Serve the app using Nginx
FROM nginx:1.21.4-alpine

# Copy the built React app from the "build" stage
COPY --from=build /app/build /usr/share/nginx/html

# Copy the Nginx configuration
COPY /nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Set the entry point to start Nginx
CMD ["nginx", "-g", "daemon off;"]