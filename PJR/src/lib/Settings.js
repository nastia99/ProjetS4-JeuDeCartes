import langs, {availableLocales} from '../lang';

export default function Settings(storageName='settings') {
  let attributes = {
    effectVolume: .5,
    vibrations: true,
    locale: 'fr',
    theme: 'default'
  }

  let res = {
    set effectVolume(value) {
      if(typeof value === 'number' && value >= 0 && value <= 1) {
        attributes.effectVolume = value;
        this.save();
      }
    },

    get effectVolume() {
      return attributes.effectVolume;
    },
    
    set theme(value) {
      attributes.theme = value;
      this.save();
    },

    get theme() {
      return attributes.theme;
    },

    set locale(value) {
      if(value in langs) {
        attributes.locale = value;
        this.save();
      }
      else
        console.warn(`Unknown locale '${value}'`);
    },

    set vibrations(value) {
      if(typeof value === 'boolean') {
        attributes.vibrations = value;
        this.save();
      }
    },

    get vibrations() {
      return attributes.vibrations;
    },

    get areVibrationsAvailable() {
      return 'vibrate' in window.navigator;
    },

    get locale() {
      return attributes.locale;
    },

    get lang() {
      return langs[attributes.locale]
    },

    get availableLocales() {
      return availableLocales;
    },
    
    save() {
      localStorage.setItem(storageName, this.toString());
    },

    toString() {
      return JSON.stringify(attributes);
    }
  }

  let presets;
  try {
    presets = JSON.parse(localStorage.getItem(storageName));
  }catch(e) {}

  if(presets && typeof presets === 'object')
    for(let setting in presets)
      if(setting in res)
        res[setting] = presets[setting];

  return res;
}