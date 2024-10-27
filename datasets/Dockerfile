FROM python:3.10

WORKDIR /app

RUN pip install --no-cache-dir pandas requests pymongo

COPY dataHandler.py .

ENTRYPOINT sleep 3600