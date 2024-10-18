FROM python:3.10

WORKDIR /app

RUN pip install --no-cache-dir pandas requests pymongo

COPY dataHandler.py .

ENTRYPOINT python3 dataHandler.py