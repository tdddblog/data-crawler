import os
import requests

def copy_file(from_url, to_path):
    if not os.path.exists(to_path):
        page = requests.get(from_url, timeout=5)
        data = page.text
        f = open(to_path, "w", encoding="utf-8")
        f.write(data)
        f.close()
