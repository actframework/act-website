/*!
* gulp
* $ npm install gulp-autoprefixer gulp-cssnano gulp-jshint gulp-concat gulp-uglify gulp-imagemin gulp-notify gulp-rename gulp-livereload gulp-cache del gulp-clip-empty-files gulp-less --save-dev
*
* Simple GULP script to show you how to integrate a Node hot-reload front-end workflow with the Act hot-reload backend and fart unicorn rainbows 4 dayz.
* - by Joe Cincotta 8/3/2017
*
*/

var gulp = require('gulp'),
    autoprefixer = require('gulp-autoprefixer'),
    cssnano = require('gulp-cssnano'),
    uglify = require('gulp-uglify'),
    rename = require('gulp-rename'),
    concat = require('gulp-concat'),
    notify = require('gulp-notify'),
    cache = require('gulp-cache'),
    livereload = require('gulp-livereload'),
    del = require('del'),
    clip = require('gulp-clip-empty-files'),
    less = require('gulp-less');

/*
* These are our ACT standard project path locations for processed CSS and Javascript files.
* */
var path = {
    css : 'src/main/resources/asset/css',
    js : 'src/main/resources/asset/js'
};


/*
* Gulp pipeline for taking standard CSS and minifying it
* then put it in the standard ACT location for static CSS resources
* */
gulp.task('css', function() {
    return gulp.src([
            'css.src/*.css',
            'node_modules/jquery-modal/jquery.modal.css'
        ])
        .pipe(rename({suffix: '.min'}))
        .pipe(cssnano())
        .pipe(gulp.dest(path.css))
        .pipe(notify({ message: 'css ok' }));
});

/*
 * Gulp pipeline for taking already minified CSS
 * then put it in the standard ACT location for static CSS resources
 * */
gulp.task('css-min', function() {
    return gulp.src([
        'node_modules/bootstrap/dist/css/bootstrap.min.css',
        'node_modules/font-awesome/css/font-awesome.min.css'
    ])
        .pipe(gulp.dest(path.css))
        .pipe(notify({ message: 'css-min ok' }));
});

/*
 * Gulp pipeline for taking LESS and processing then minifying it
 * then put it in the standard ACT location for static CSS resources
 *
 * Note that, in this case, we have a rsrc directory with a third party bootstrap template.
 * */
gulp.task('less', function() {
    return gulp.src('less.src/*.less')
        .pipe(less())
        .pipe(rename({suffix: '.min'}))
        .pipe(cssnano())
        .pipe(clip())
        .pipe(gulp.dest(path.css))
        .pipe(notify({ message: 'less ok' }));
});

/*
 * Gulp pipeline for taking already minified JS from node_modules and
 * then put it in the standard ACT location for static JS resources
 * */
gulp.task('js-min', function() {
    return gulp.src([
            'node_modules/jquery/dist/jquery.min.js',
            'node_modules/markdown-it/dist/markdown-it.min.js',
            'node_modules/scrollreveal/dist/scrollreveal.min.js',
            'node_modules/bootstrap/dist/js/bootstrap.min.js'
        ])
        .pipe(gulp.dest(path.js))
        .pipe(notify({ message: 'js-min ok' }));
});

/*
 * Gulp pipeline for taking Javascript and minifying it,
 * then put it in the standard ACT location for static JS resources
 *
 * Note: we are using prismjs, however since there are no compiled CSS distribution files in the PrismJS deployment
 * we use pre-compiled versions in the js.src and css.src directories... don't hate me.
 * */
gulp.task('js', function() {
    return gulp.src([
            'js.src/*.js',
            'node_modules/jquery-modal/jquery.modal.js'
        ])
        .pipe(rename({suffix: '.min'}))
        .pipe(uglify())
        .pipe(gulp.dest(path.js))
        .pipe(notify({ message: 'js ok' }));
});

/*
 * Gulp cleanup task
 * */
gulp.task('clean', function() {
    return del([
        path.js + '/*.js',
        path.css + '/*.css'
    ]);
});

/*
 * Gulp default task
 * */
gulp.task('default', ['clean'], function() {
    gulp.start('js', 'js-min', 'css','css-min','less');
});

/*
 * Gulp file watcher only needs to monitor changes to actual CSS and JS files
 * since the ACT hot-reload takes care of all other changes
 * */
gulp.task('watch', function() {
    // Watch .css files
    gulp.watch('css.src/*.css', ['css']);

    // Watch .js files
    gulp.watch('js.src/*.js', ['js']);

    // Watch .js files
    gulp.watch('less.src/*.less', ['less']);

    // Create LiveReload server
    livereload.listen();
});
