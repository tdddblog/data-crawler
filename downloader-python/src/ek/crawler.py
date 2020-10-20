import os
import requests

import ek.utils
from ek.parser import MyHTMLParser


class Crawler:
    def __init__(self, base_url, path, save_to):
        self.base_url = base_url
        self.path = path
        self.save_to = save_to

    def run(self):
        self.process_path(self.path)

    def process_path(self, base_path):
        print("Processing " + base_path)

        save_path = self.save_to + base_path
        if not os.path.exists(save_path):
            os.makedirs(save_path)

        page = requests.get(self.base_url + base_path, timeout=15)
        data = page.text

        # Parse HTML and extract hrefs
        parser = MyHTMLParser(base_path)
        parser.feed(data)
        parser.close()

        # Files
        for f in parser.files:
            from_url = self.base_url + base_path + f
            to_path = self.save_to + base_path + f
            # Copy file
            ek.utils.copy_file(from_url, to_path)

        # Dirs
        for f in parser.dirs:
            self.process_path(base_path + f)
