FROM alpine/git AS INIT
ARG GIT_TOKEN=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
WORKDIR /project
RUN git clone -b master https://$GIT_TOKEN@github.com/macroscode/mds-portal-ui.git



FROM node:alpine AS BUILD
COPY --from=INIT /project/mds-portal-ui /app
WORKDIR /app

COPY package*.json /app/
RUN npm install
COPY . /app/
COPY prod-environment.ts /app/src/environment.ts
RUN npm run build --prod


FROM nginx:alpine
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=BUILD /app/dist/mds-portal-ui /usr/share/nginx/html