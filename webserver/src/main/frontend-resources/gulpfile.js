var gulp = require('gulp');
var sass = require('gulp-sass');
var copy = require('gulp-copy');
var rename = require('gulp-rename');
var livereload = require('gulp-livereload');


//Compilation et copy des fichiers SASS
gulp.task('sass', function () {
    gulp.src('./scss/*.scss')
        .pipe(sass({sourceComments: 'map'}))
        .pipe(sass.sync().on('error', sass.logError))
        .pipe(gulp.dest('../webapp/inc/css'))
        .pipe(livereload());
});

//Déplacement des plugins depuis node_modules(npm)
gulp.task('js-plugins', function () {
    gulp.src([
        './node_modules/jquery/dist/jquery.min.js',
        './node_modules/bootstrap/dist/js/bootstrap.min.js',
        './node_modules/popper.js/dist/umd/popper.min.js',
    ]).pipe(gulp.dest('../webapp/inc/js/ext', {overwrite: true}));
});

gulp.task('js', function () {
    gulp.src([
        './js/*.js'
    ]).pipe(gulp.dest('../webapp/inc/js', {overwrite: true}));
});

gulp.task('default', ['sass']);

gulp.task('build', ['js', 'sass', 'js-plugins']);

gulp.task('all', ['sass', 'js-plugins']);