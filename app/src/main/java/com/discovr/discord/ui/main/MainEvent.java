package com.discovr.discord.ui.main;

public interface MainEvent {
    final class RollDice implements MainEvent {
        public RollDice() {
        }
    }

    final class SwipeLeft implements MainEvent {
        private final int position;

        public SwipeLeft(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    final class SwipeRight implements MainEvent {
        private final int position;

        public SwipeRight(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }
    }

    final class LoadCards implements MainEvent {
    }
}
