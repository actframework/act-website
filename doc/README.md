# ActFramework web site project

##Getting Started
Ok, so this project is the 'official' Act.Framework website. As such, it's a slightly less trivial example of building a website in Act - which is of course an experience of pure ecstacy.

Some things to know about this project which are different from other examples is that we have a Node/Gulp pipeline in here.
  
To get your environment set up you should have NPM, Node.JS installed and Gulp installed globally via NPM. You should also have MongoDB installed and running.

Once you have these prerequisites, you can get the dependencies by using
```
./setup_dev 
```
or, if you have issues from your NPM install complaining about permissions you can use
```
sudo ./setup_dev 
```

To start developing with this project, you can then use two shell windows. In one, type this:
```
gulp watch
```
and in the other one, type this...
```
./run_dev
```

This will run the Act.Framework hot-reload server as well as the Gulp filewatcher to keep a filewatcher running which will automatically compile, copy, minify, uglify etc. the scripts from the following directories and automagically install them into your running Act project. This means you can edit *anything* - CSS, LESS, Javascript, and know it will work right every time you refresh the browser!

These are the directories we use for the actual source files: 
```
js.src
css.src
less.src
```
So, once you start building project you will notice that we have predefined convention for JS and CSS files to be put in an Act.Framework project. This directory must be left EMPTY since it will be cleaned out by the Gulp build script every time the code is changed.

The standard directories you should not put custom code into are
```
    css : 'src/main/resources/asset/css',
    js : 'src/main/resources/asset/js'

```
You will notice that you have a nicely commented gulpfile.js file to refer to which is designed to make reusing this approach for your own projects super easy
   
So get in there and code like it's a 1999 dotcom bubble...
