import antfu from '@antfu/eslint-config'
import NuxtEslintConfig from './.nuxt/eslint.config.mjs'

export default antfu(
  {
    vue: true,
    typescript: true,
    ignores: ['components/ui/**'],
  },
  NuxtEslintConfig,
)
