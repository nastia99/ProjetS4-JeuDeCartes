import React, { Component } from 'react';
import SummerImg from './assets/summer.jpg';
import EasterImg from './assets/easter.jpg';
import Halloween from './assets/halloween.png';
import Christmas from './assets/christmas.jpg';
import DefaultImg from './assets/bckg.jpg';

export const themes = [
  {
    title: {
      fr: 'Défaut',
      en: 'Default'
    },
    key: 'default',
    src: DefaultImg
  },
  {
    title: {
      fr: 'Noël',
      en: 'Christmas'
    },
    key: 'christmas',
    src: Christmas
  },
  {
    title: {
      fr: 'Eté',
      en: 'Summer'
    },
    key: 'summer',
    src: SummerImg
  },
  {
    title: {
      fr: 'Pâques',
      en: 'Easter'
    },
    key: 'easter',
    src: EasterImg
  },
  {
    title: {
      fr: 'Halloween',
      en: 'Halloween'
    },
    key: 'halloween',
    src: Halloween
  }
]

export function getThemes(locale) {
  return themes.map(t =>
    ({...t, title: t.title[locale]})
  );
}

export function getTheme(key) {
  return themes.find(t => t.key === key);
}

export class BackgroundTheme extends Component {
  render() {
    const {settings, ...others} = this.props;
    const {theme} = settings;
    const currentTheme = getTheme(theme);

    return (
      <div {...others} style={{overflow: 'auto', backgroundImage: `url('${currentTheme.src}')`}}>
        {this.props.children}
      </div>
    );
  }
}