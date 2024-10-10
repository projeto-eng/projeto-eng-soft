import os
import requests
import zipfile
import pandas as pd
from pymongo import MongoClient
from io import BytesIO

# Configuration
zip_url = 'https://download.inep.gov.br/microdados/enem_por_escola/2005_a_2015/microdados_enem_por_escola.zip'  # Replace with your .zip file URL
local_json_path = 'path/to/your/local_file.json'  # Replace with your local .json file path

# MongoDB credentials from environment variables
username = os.getenv('MONGO_INITDB_ROOT_USERNAME')
password = os.getenv('MONGO_INITDB_ROOT_PASSWORD')
mongo_uri = f'mongodb://{username}:{password}@container_1:27017/'
database_name = 'projeto-eng-soft'  # Base database name

def download_zip(url):
    response = requests.get(url)
    response.raise_for_status()  # Raise an error for bad responses
    return BytesIO(response.content)

def extract_csv_from_zip(zip_file):
    csv_dataframes = []
    with zipfile.ZipFile(zip_file) as z:
        for filename in z.namelist():
            if filename.endswith('.csv'):
                with z.open(filename) as f:
                    # Read CSV with ANSI encoding and handle bad lines
                    try:
                        dataframe = pd.read_csv(
                            f,
                            encoding='latin1',
                            sep=';',  # Change this if your CSV uses a different delimiter
                            on_bad_lines='skip',  # Log a warning for bad lines
                            dtype={20: str}
                        )
                        csv_dataframes.append((filename, dataframe))
                    except Exception as e:
                        print(f"Failed to read {filename}: {e}")
    if not csv_dataframes:
        raise ValueError("No .csv files found in the zip.")
    return csv_dataframes


def read_local_json(file_path):
    if not os.path.exists(file_path):
        raise FileNotFoundError(f"The file {file_path} does not exist.")
    # Read the JSON file into a DataFrame
    dataframe = pd.read_json(file_path)
    return [(os.path.basename(file_path), dataframe)]

def insert_data_to_mongodb(dataframes):
    client = MongoClient(mongo_uri)
    for filename, dataframe in dataframes:
        # Create a database for each .csv file
        collection_name = os.path.splitext(filename)[0]  # Use the filename without extension as the collection name
        db = client[database_name]
        collection = db[collection_name]
        
        # Convert dataframe to dictionary and insert into MongoDB
        records = dataframe.to_dict(orient='records')
        collection.insert_many(records)

def main():
    debug_mode = os.getenv('DEBUG_MODE', '0')  # Default to '0' if not set
    try:
        if debug_mode == '1':
            # Development mode: read from local CSV
            print("Development mode is on. Reading local CSV.")
            dataframes = read_local_csv(local_csv_path)
        else:
            zip_file = download_zip(zip_url)

            dataframes = extract_csv_from_zip(zip_file)

        # Insert data into MongoDB
        if debug_mode == '1':
            print("Dataframe allocated successfully, sending to MongoDB.")
        insert_data_to_mongodb(dataframes)

        if debug_mode =='1':
            print("Data successfully inserted into MongoDB!")
        
    except Exception as e:
        print(f"An error occurred: {e}")

if __name__ == '__main__':
    main()
