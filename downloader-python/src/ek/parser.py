from html.parser import HTMLParser


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
            elif href.endswith("/") and not (href.startswith("/") or href.startswith("http")):
                self.dirs.add(href)

    def close(self):
        super().close()
