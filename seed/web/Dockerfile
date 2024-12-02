FROM node:23

WORKDIR /

RUN apt update && apt install git

RUN git clone https://github.com/projeto-eng/front-projeto-eng.git

WORKDIR /front-projeto-eng

RUN npm install

CMD ["npm", "start"]