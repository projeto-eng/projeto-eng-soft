from abc import ABC, abstractmethod
import os

class ProcessFile(ABC):
    def __init__(self, file_url):
            self.file_url = file_url
            self.dataframes = []

            # MongoDB credentials from environment variables
            self.username = os.getenv('MONGO_INITDB_ROOT_USERNAME', '1')
            self.password = os.getenv('MONGO_INITDB_ROOT_PASSWORD', '1')
            self.mongo_uri = f'mongodb://{self.username}:{self.password}@container_1:27017/'
            self.database_name = 'projeto-eng-soft'  # Base database name

    @abstractmethod
    def importa(self):
        pass

    @abstractmethod
    def exporta(self):
        pass

    @staticmethod
    def create_processor(file_url):
        filename = os.path.splitext(os.path.basename(file_url))[0]

        if filename == 'microdados_enem_por_escola':
            from processEnemEscola import ProcessEnemEscola
            return ProcessEnemEscola(file_url)