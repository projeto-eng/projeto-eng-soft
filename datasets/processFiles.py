import os
import requests
import zipfile
import json
import pandas as pd
from pymongo import MongoClient
from io import BytesIO

class ProcessFiles():
    def __init__(self):

            # MongoDB credentials from environment variables
            self.username = os.getenv('MONGO_INITDB_ROOT_USERNAME', 'user1')
            self.password = os.getenv('MONGO_INITDB_ROOT_PASSWORD', 'user1')
            self.mongo_uri = f'mongodb://{self.username}:{self.password}@container_1:27017/'
            self.database_name = 'projeto-eng-soft'  # Base database name

    def __download_file(self, file_url):
        response = requests.get(file_url)
        response.raise_for_status()
        return BytesIO(response.content)

    def __extract_data_from_zip(self, file):
        dataframes = []

        with zipfile.ZipFile(file) as z:
            for filename in z.namelist():
                if filename.endswith('.csv'):
                    with z.open(filename) as f:
                        # Read CSV with ANSI encoding and handle bad lines
                        try:
                            dataframe = pd.read_csv(
                                f,
                                encoding='cp1252',
                                sep=';',
                                on_bad_lines='skip',
                                dtype={20: str},
                                low_memory = False
                            )
                            filename = filename.split('/')[-1]
                            dataframes.append((filename, dataframe))
                        except Exception as e:
                            print(f"Failed to read {filename}: {e}")
        if not dataframes:
            raise ValueError("No .csv files found in the zip.")
        return dataframes

    def __extract_data_from_json(self, filepath):
        dataframes = []
        # Check if the file exists
        if not os.path.exists(filepath):
            raise FileNotFoundError(f"File {filepath} not found.")

        # Read the JSON file
        with open(filepath, 'r', encoding='utf-8') as file:
            data = json.load(file)

        # Convert the JSON to a dataframe
        temp_dataframe = pd.json_normalize(data)

        # Return a list with one tuple (filename, dataframe)
        return dataframes.append[(os.path.basename(file_path), temp_dataframe)]

    def __save_as_json(self, dataframes, filepath):
        # Create the 'datasets' directory if it doesn't exist
        os.makedirs('datasets', exist_ok=True)

        for filename, dataframe in dataframes:
            try:
                dataframe.to_json(filepath, orient='records', lines=False, force_ascii=False)
            except Exception as e:
                print(f"Failed to save DataFrame as JSON for {filename}: {e}")

    def __insert_data_to_mongodb(self, dataframes):
        client = MongoClient(self.mongo_uri)
        db = client[self.database_name]

        for filename, dataframe in dataframes:
            # Create a database for each .csv file
            collection_name = os.path.splitext(filename)[0]  # Use the filename without extension as the collection name

            if collection_name in db.list_collection_names():
                print(f"Collection '{collection_name}' already exists. Skipping insertion.")
                continue

            collection = db[collection_name]

            # Convert dataframe to dictionary and insert into MongoDB
            records = dataframe.to_dict(orient='records')

            collection.insert_many(records)

    def __check_dev_files(self, filepath):
        if os.path.exists(filepath):
            return '1'
        return '0'

    def importaURL(self, file_url):
        filename = file_url.split('/')[-1].replace('.zip', '')
        filepath = 'datasets/' + filename + '.json'

        dev_mode = self.__check_dev_files(filepath)

        dataframes = []

        try:
            if dev_mode == '1':
                dataframes = self.__extract_data_from_json(filepath)

            else:
                zipfile = self.__download_file(file_url)

                dataframes = self.__extract_data_from_zip(zipfile)

                self.__save_as_json(dataframes, filepath)

        except Exception as e:
            print(f"import: An error occurred: {e}")

        return dataframes

    def exportaMongo(self, dataframes):
        try:
            self.__insert_data_to_mongodb(dataframes)

        except Exception as e:
            print(f"An error occurred: {e}")

