import fr from './fr';
import en from './en';

const langs = {
  fr,
  en
}

const availableLocales = Object.entries(langs)
  .map(({[0]:code, [1]:lang}) => ({
    code,
    name: lang.langName
  }));

export default langs;
export {availableLocales};