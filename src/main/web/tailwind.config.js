module.exports = {
  darkMode: 'class',
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {

    extend: {
      backgroundImage:{
        'home-page': "url('/home_page_images/background.png')",
      },
      colors: {
        'light-mode-hover-green': '#00BA34',
        'light-mode-green': '#00A72E',
        'dark-mode-hover-green': '#66D585',
        'dark-mode-green': '#4CCE70',
      },
    },
  },
  plugins: [],
}
