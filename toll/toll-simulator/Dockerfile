FROM node:8-alpine

ENV SERVICES=api,simulator,status
ENV NODE_ENV=production
ENV SERVICEDIR=services

RUN mkdir /app
WORKDIR /app

COPY package.json .

RUN npm install --production

COPY . .

CMD ["npm", "start"]