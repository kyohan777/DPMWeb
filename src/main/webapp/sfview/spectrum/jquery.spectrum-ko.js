// Spectrum Colorpicker
// Korean (ko) localization
// https://github.com/bgrins/spectrum

(function ( $ ) {

    var localization = $.spectrum.localization["ko"] = {
        cancelText: "취소",
        chooseText: "선택",
        clearText: "선택 초기화",
        noColorSelectedText: "선택된 색상 없음",
        togglePaletteMoreText: ">>",
        togglePaletteLessText: "<<"
    };

    $.extend($.fn.spectrum.defaults, localization);

})( jQuery );
