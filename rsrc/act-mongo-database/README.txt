Author: CINJ (Joe Cincotta) joe@thinking.group
Date: 14/3/2017

These files are BSON files used by MongoDB to restore the website database.

To restore an example dataset for Skeletons and Videos, just use `mongorestore -d act_website <the file name>`

You can change the name of the database in the common/app.properties (this is the name you specify with the -d switch)

Note: these files are NOT TRACKED so you cannot rely on these files to be the latest data on the live ACT.Framework website... They are just here to help with the development of the site - since you will need them to test the rendering of skeletons and videos.




