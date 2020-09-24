import os
import requests
from html.parser import HTMLParser

# --------------------------------------------------------------

class MyHTMLParser(HTMLParser):
    def __init__(self, base_path):
        super().__init__()
        self.files = set()
        self.dirs = set()

    def handle_starttag(self, tag, attrs):
        if tag == 'a':
            href = attrs[0][1]
            if href.endswith(".xml"):
                self.files.add(href)
            elif href.endswith("/") and not href.startswith("/"):
                self.dirs.add(href)

    def close(self):
        super().close()

# --------------------------------------------------------------

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
        copy_files = False
        if not os.path.exists(save_path):
            os.makedirs(save_path)
            copy_files = True

        page = requests.get(self.base_url + base_path)
        data = page.text

        # Parse HTML and extract hrefs
        parser = MyHTMLParser(base_path)
        parser.feed(data)
        parser.close()

        # Files
        if copy_files:
            for f in parser.files:
                from_url = self.base_url + base_path + f
                to_path = self.save_to + base_path + f
                #print("  " + from_url + "  -->  " + to_path)
                # Copy file
                page = requests.get(from_url)
                data = page.text
                f = open(to_path, "w")
                f.write(data)
                f.close()

        # Dirs
        for f in parser.dirs:
            self.process_path(base_path + f)
        
# --------------------------------------------------------------


crawler = Crawler("https://pds-imaging.jpl.nasa.gov", "/data/nsyt/insight_cameras/", "/tmp/insight")
crawler.run()

