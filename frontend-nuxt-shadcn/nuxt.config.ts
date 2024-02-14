// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  ssr: false,
  modules: [
    '@nuxt/image',
    '@nuxtjs/tailwindcss',
    'nuxt-module-eslint-config',
    'shadcn-nuxt',
  ],
  shadcn: {
    /**
     * Prefix for all the imported component
     */
    prefix: '',
    /**
     * Directory that the component lives in.
     * @default "./components/ui"
     */
    componentDir: './components/ui',
  },
  image: {
    cloudflare: {
      baseURL: 'https://img.ssafy.coffee',
    },
  },
  vite: {
    server: {
      proxy: {
        '/api': {
          target: 'http://localhost',
          changeOrigin: true,
        },
      },
    },
  },
})
