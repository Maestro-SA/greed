module.exports = {
  content: [
    './src/**/*',
    './resources/**/*',
  ],
  theme: {
    extend: {
      fontFamily: {
        "giza": ["Giza", "sans-serif"],
        "giza-stencil": ["Giza-Stencil", "sans-serif"],
      }
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
}
