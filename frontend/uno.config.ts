import {
  defineConfig,
  presetAttributify,
  presetIcons,
  presetTypography,
  presetUno,
  presetWebFonts,
  transformerDirectives,
  transformerVariantGroup,
} from 'unocss'

export default defineConfig({
  shortcuts: [
    ['b-color', 'b-#1e293b'],
    ['b-hover', 'hover:b-#343844'],
    ['bg-color', 'bg-#1e293b'],
    ['bg-hover', 'hover:bg-#343844'],
    ['card', 'b-3 b-color rounded-2'],
    ['card-shadow', 'shadow-[2px_2px_2px_2px] shadow-gray-2'],
    ['btn', 'card bg-color p-x-2 p-y-1 text-white card-shadow disabled:b-gray b-hover bg-hover disabled:bg-gray disabled:hover:bg-gray'],
  ],
  presets: [
    presetUno(),
    presetAttributify(),
    presetIcons({
      scale: 1.2,
    }),
    presetTypography(),
    presetWebFonts({
      provider: 'google',
      fonts: {
        sans: 'Gowun Dodum',
      },
    }),
  ],
  transformers: [
    transformerDirectives(),
    transformerVariantGroup(),
  ],
  preflights: [
    { getCSS: () => '::-webkit-scrollbar { display: none; }' },
  ],
})
