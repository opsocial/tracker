
$('.nav-link').click((e) => {
    e.preventDefault();

    var id = $(this).attr("href");
    console.log(id);

    targetOfSet = $(id).offset();

    console.log(targetOfSet);
})
