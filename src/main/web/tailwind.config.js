module.exports = {
  darkMode: 'class',
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {

    extend: {
      backgroundImage:{
        'light-home': "url('/backgroundImage.png')",
        'dark-home': "url('/background_dark_home.png')",
      }
    },
  },
  plugins: [],
}
