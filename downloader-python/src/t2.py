from ek.crawler import Crawler


#crawler = Crawler("https://pds-imaging.jpl.nasa.gov", "/data/nsyt/insight_cameras/", "/tmp/insight")

crawler = Crawler("https://sbnarchive.psi.edu", "/pds4/orex/orex.ocams/", "/tmp/orex")
crawler.run()

