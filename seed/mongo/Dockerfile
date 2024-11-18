FROM mongo:8.0

RUN apt update && apt install locales

RUN sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen && \
    dpkg-reconfigure --frontend=noninteractive locales && \
    update-locale LANG=en_US.UTF-8

ENV LANG en_US.UTF-8

RUN apt install -y python3 python3-pandas python3-requests python3-pymongo python3-wget

WORKDIR /seed/mongo

ENTRYPOINT /seed/mongo/import.sh