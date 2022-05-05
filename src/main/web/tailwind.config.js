module.exports = {
  darkMode: 'class',
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {

    extend: {
      backgroundImage:{
        'light-home': "url('/lightBackground1.png')",
        'dark-home': "url('/darkBackground.png')",
      },
      colors: {
        'emerald-green': '#004400',
        'forest-green': '#00762E',
        'lint': '#BACC81',
        'light-lint': '#CDD193',
        'kelly-green': '#74E39A',
      }
    },
  },
  plugins: [],
}
