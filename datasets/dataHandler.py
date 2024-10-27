import os
import requests
import zipfile
import json
import pandas as pd
from pymongo import MongoClient
from io import BytesIO

# Configuration
zip_url = 'https://download.inep.gov.br/microdados/enem_por_escola/2005_a_2015/microdados_enem_por_escola.zip'  # Replace with your .zip file URL

# MongoDB credentials from environment variables
username = os.getenv('MONGO_INITDB_ROOT_USERNAME', '1')
password = os.getenv('MONGO_INITDB_ROOT_PASSWORD', '1')
mongo_uri = f'mongodb://{username}:{password}@container_1:27017/'
database_name = 'projeto-eng-soft'  # Base database name

def download_zip(url):
    response = requests.get(url)
    response.raise_for_status()  # Raise an error for bad responses
    return BytesIO(response.content)

def filter_dataframe(dataframe):
    # Define the columns of interest
    relevant_information = ['NU_ANO', 'CO_UF_ESCOLA', 'SG_UF_ESCOLA', 'NO_MUNICIPIO_ESCOLA']

    # Filter the DataFrame to only include the columns of interest
    filtered_dataframe = dataframe[relevant_information]

    return filtered_dataframe

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
                        filename = filename.split('/')[-1]
                        dataframe = filter_dataframe(dataframe)
                        csv_dataframes.append((filename, dataframe))
                    except Exception as e:
                        print(f"Failed to read {filename}: {e}")
    if not csv_dataframes:
        raise ValueError("No .csv files found in the zip.")
    return csv_dataframes

def read_local_json():
    folder_path = 'datasets'

    # List to hold tuples (filename, dataframe)
    dataframes = []

    # Iterate over all files in the folder
    for filename in os.listdir(folder_path):
        if filename.endswith('.json'):
            file_path = os.path.join(folder_path, filename)

            # Read the JSON file
            with open(file_path, 'r', encoding='latin1') as file:
                data = json.load(file)

            # Convert the JSON to a dataframe
            temp_dataframe = pd.json_normalize(data)

            # Append tuple (filename, dataframe) to the list
            dataframes.append((filename, temp_dataframe))

    if not dataframes:
        raise ValueError("No .json files found in the folder.")

    return dataframes

def save_dataframe_as_json(csv_dataframes):
    # Create the 'datasets' directory if it doesn't exist
    os.makedirs('datasets', exist_ok=True)

    for filename, dataframe in csv_dataframes:
        # Define the full path for the JSON file
        json_file_path = os.path.join('datasets', filename.replace('.csv', '.json'))

        # Save the DataFrame as a JSON file
        try:
            dataframe.to_json(json_file_path, orient='records', lines=False, force_ascii=False)
            print(f"DataFrame saved as JSON at: {json_file_path}")
        except Exception as e:
            print(f"Failed to save DataFrame as JSON for {filename}: {e}")

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

def check_dev_files():
    folder_path = 'datasets'

    # Check if the folder exists
    if os.path.exists(folder_path):
        # List files in the folder
        files = os.listdir(folder_path)
        # Check for any .json files
        if any(file.endswith('.json') for file in files):
            return '1'  # There is at least one .json file
    return '0'  # No .json files found or folder doesn't exist

def main():
    dev_mode = check_dev_files()

    try:
        if dev_mode == '1':
            # Development mode: read from local CSV
            print("Development mode is on. Reading local json.")
            dataframes = read_local_json()
        else:
            zip_file = download_zip(zip_url)

            dataframes = extract_csv_from_zip(zip_file)

            save_dataframe_as_json(dataframes)

        # Insert data into MongoDB
        if dev_mode == '1':
            print("Dataframe allocated successfully, sending to MongoDB.")

        insert_data_to_mongodb(dataframes)

        if dev_mode =='1':
            print("Data successfully inserted into MongoDB!")
        
    except Exception as e:
        print(f"An error occurred: {e}")

if __name__ == '__main__':
    main()
